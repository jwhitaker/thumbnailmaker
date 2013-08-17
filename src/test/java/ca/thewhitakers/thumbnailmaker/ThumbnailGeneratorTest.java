package ca.thewhitakers.thumbnailmaker;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
	public void shouldResizeSameSizeCorrectly() throws IOException {
		Dimension d = new Dimension(250, 200);
		
		// Create a sample thumbnail
		final InputStream input = ClassLoader.getSystemResourceAsStream("sample.jpg");
		final OutputStream output = new FileOutputStream(new File("target/test2_1.png"));
		generator.generate(input, output, d);
		output.close();
		input.close();
		
		// Resize correctly on existing thumbnail
		final InputStream input2 = new FileInputStream(new File("target/test2_1.png"));
		final OutputStream output2 = new FileOutputStream(new File("target/test2_2.png"));
		generator.generate(input2, output2, d);
		output2.close();
		input2.close();
		
		// As of right now this test is completed based on visual inspection.
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
