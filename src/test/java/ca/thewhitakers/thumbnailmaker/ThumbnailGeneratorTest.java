package ca.thewhitakers.thumbnailmaker;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThumbnailGeneratorTest {
	private ThumbnailGenerator generator;
	
	@Before
	public void setup() {
		generator = new ThumbnailGenerator();
	}
	
	@Test
	public void inputStream_shouldGenerateThumbnail() throws IOException {
		final InputStream input = ClassLoader.getSystemResourceAsStream("sample.jpg");
		final OutputStream output = new FileOutputStream(new File("target/test.png"));
		
		generator.generate(input, output, new Dimension(250, 200));
		
		BufferedImage bi = ImageIO.read(new File("target/test.png"));
		
		Assert.assertEquals(bi.getWidth(), 250);
		Assert.assertEquals(bi.getHeight(), 200);
	}
	
	@Test
	public void byteArray_shouldGenerateThumbnail() throws IOException {
		final InputStream input = ClassLoader.getSystemResourceAsStream("sample.jpg");
		byte[] inputImage = IOUtils.toByteArray(input);
		
		byte[] outputImage = generator.generate(inputImage, new Dimension(250, 250));
		
		Assert.assertNotNull(outputImage);
		Assert.assertTrue(outputImage.length > 0);
	}
}
