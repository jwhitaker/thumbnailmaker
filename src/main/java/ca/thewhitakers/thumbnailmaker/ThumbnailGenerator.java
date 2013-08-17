package ca.thewhitakers.thumbnailmaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * A very very simple thumbnail generator.
 * 
 * @author Jason Whitaker
 * 
 */
public class ThumbnailGenerator {
	/**
	 * Generates a thumbnail, while maintaining the aspect ratio, from the input byte[], to the specified dimension.
	 * 
	 * @param inputImage The raw bytes of the image.
	 * @param boundary The size fo the thumbnail to generate
	 * @return The newly resized image as a byte[]
	 * @throws IOException
	 */
	public byte[] generate(byte[] inputImage, Dimension boundary) throws IOException {
		InputStream is = new ByteArrayInputStream(inputImage);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		generate(is, os, boundary);
		
		return os.toByteArray();
	}
	
	/**
	 * Generates a thumbnail, while maintaining the aspect ratio, from the input stream, to the specified dimension.
	 * 
	 * @param inputStream An InputStream specifying the source image.
	 * @param outputStream Where the scaled image is written to.
	 * @param boundary The size of the thumbnail to generate.
	 * @throws IOException
	 */
	public void generate(InputStream inputStream, OutputStream outputStream, Dimension boundary) throws IOException {
		BufferedImage image = ImageIO.read(inputStream);		
		Dimension scaledDimensions = getScaledDimension(new Dimension(image.getWidth(),  image.getHeight()), boundary);
				
		BufferedImage newImage = new BufferedImage(boundary.width, boundary.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		
		Color c = new Color(255, 255, 255, 0);
		g.setColor(c);
		g.fillRect(0, 0, boundary.width, boundary.height);
		g.drawImage(image, 0, 0, scaledDimensions.width, scaledDimensions.height, null);
		g.dispose();
		
		ImageIO.write(newImage, "png", outputStream);
	}
	
	/**
	 * Calculate the new size of a dimension while maintaining the aspect ratio.
	 * 
	 * @param originalSize The original size of the image.
	 * @param boundary The boundary, or thumbnail size to generate to.
	 * @return The new dimension
	 */
	private Dimension getScaledDimension(Dimension originalSize, Dimension boundary) {
		int newWidth = originalSize.width;
		int newHeight = originalSize.height;
		
		if (originalSize.width >= boundary.width) {
			newWidth = boundary.width;
			newHeight = (newWidth * originalSize.height) / originalSize.width;
		}
		
		if (newHeight >= boundary.height) {
			newHeight = boundary.height;
			newWidth = (newHeight * originalSize.width) / originalSize.height;
		}
		
		return new Dimension(newWidth, newHeight);
	}
}

