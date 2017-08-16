package cif.core;

import java.awt.image.BufferedImage;

import cif.Compressor;
import cif.Decompressor;
import cif.convenience.Benchmark;
import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileReader.ReadAs;
import cif.convenience.FileUtils.FileWriter.WriteAs;
import cif.convenience.Print;
import cif.convenience.Unit;

@SuppressWarnings("unused")
public class Main {
	private String input = "C:\\Users\\Alex K. Quilliam\\Desktop\\input.png";
	private String cifOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.cif";
	private String pngOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.png";
	
	@SuppressWarnings("unchecked")
	public Main() {
		Benchmark.start(0);
		BufferedImage image = (BufferedImage) FileUtils.FileReader.read(input, ReadAs.BUFFEREDIMAGE);
		Benchmark.endAndPrint(0, "Reading the pixeldata (from a PNG file) took: ", Unit.MILLISECONDS);
		
		String compressedData = new Compressor(image).getData();
		
		Benchmark.start(30);
		FileUtils.FileWriter.write(cifOutput, compressedData, WriteAs.PLAINTEXT);
		Benchmark.endAndPrint(30, "Writing the primary and secondary compression results (to a CIF file) took: ", Unit.MILLISECONDS);
		
		BufferedImage decompressedData = new Decompressor(compressedData).getData();
		
		Benchmark.start(31);
		FileUtils.FileWriter.write(pngOutput, decompressedData, WriteAs.PNG);
		Benchmark.endAndPrint(31, "Writing the primary and secondary decompression results (to a PNG file) took: ", Unit.MILLISECONDS);
		
		Print.ln("\nFile size is " + FileUtils.getFileSize(cifOutput, Unit.KILOBYTES) + " kilobytes, up from " + FileUtils.getFileSize(pngOutput, Unit.KILOBYTES) + " kilobytes\n");
	}

	public static void main(String[] args) {
		Benchmark.start(20);
		new Main();
		Benchmark.endAndPrint(20, "Total program excecution time took: ", Unit.MILLISECONDS);
	}
}