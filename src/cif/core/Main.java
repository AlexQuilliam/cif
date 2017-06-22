package cif.core;

import java.util.List;

import cif.convenience.Benchmark;
import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileReader.ReadAs;
import cif.convenience.FileUtils.FileWriter.WriteAs;
import cif.convenience.GlobalUtils;
import cif.convenience.Print;
import cif.convenience.Unit;
import cif.core.primary.PrimaryCompressor;
import cif.core.primary.PrimaryDecompressor;
import cif.core.secondary.SecondaryCompressor;
import cif.core.secondary.SecondaryDecompressor;
import cif.core.secondary.compressed.CSecondaryPatternSet;

@SuppressWarnings("unused")
public class Main {
	private String input = "C:\\Users\\Alex K. Quilliam\\Desktop\\input.png";
	private String textOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.txt";
	private String pngOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.png";

	@SuppressWarnings("unchecked")
	public Main() {		
		//prerequsites
		Benchmark.start(19);
		GlobalUtils.pixelData = (List<List<Integer>>) FileUtils.FileReader.read(input, ReadAs.PIXELDATA);
		Benchmark.endAndPrint(19, "Reading the pixeldata took: ", Unit.MILLISECONDS);
		GlobalUtils.pixelData = GlobalUtils.flipData(GlobalUtils.pixelData);
		
		Benchmark.start(4);
		GlobalUtils.pixelData = new PixelBlender(GlobalUtils.pixelData).getBlendedData();
		Benchmark.endAndPrint(4, "Pixel blending took: ", Unit.MILLISECONDS);
		
		//primary compression
		Benchmark.start(6);
		String pCompressionResult = new PrimaryCompressor(GlobalUtils.pixelData).getPCompressedData();
		Benchmark.endAndPrint(6, "Primary compression took: ", Unit.MILLISECONDS);
		
		//secondary compression
		Benchmark.start(10);
		String sCompressedData = new SecondaryCompressor(pCompressionResult).getUSCompressedData();
		Benchmark.endAndPrint(10, "Secondary compressor took: ", Unit.MILLISECONDS);
		
		Benchmark.start(30);
		FileUtils.FileWriter.write(textOutput, sCompressedData, WriteAs.PLAINTEXT);
		Benchmark.endAndPrint(30, "Writing the primary and secondary compression results took: ", Unit.MILLISECONDS);
		
		//secondary decompression
		Benchmark.start(11);
		String sDecompressedData = new SecondaryDecompressor(sCompressedData).getUSDecompressedData();
		Benchmark.endAndPrint(11, "Secondary decompressor took: ", Unit.MILLISECONDS);
		
		//primary decompression
		Benchmark.start(1);
		List<List<Integer>> decompressedPixelData = new PrimaryDecompressor(sDecompressedData).getPixelData();
		Benchmark.endAndPrint(1, "Primary decompression took: ", Unit.MILLISECONDS);
		
		Benchmark.start(31);
		FileUtils.FileWriter.write(pngOutput, decompressedPixelData, WriteAs.PNG);
		Benchmark.endAndPrint(31, "Writing the primary and secondary decompression results took: ", Unit.MILLISECONDS);
		
		Print.ln("\nFile size is " + FileUtils.getFileSize(textOutput, Unit.KILOBYTES) + " kilobytes, down from " + FileUtils.getFileSize(pngOutput, Unit.KILOBYTES) + " kilobytes\n");
	}

	public static void main(String[] args) {
		Benchmark.start(20);
		new Main();
		Benchmark.endAndPrint(20, "Total program excecution time took: ", Unit.MILLISECONDS);
	}
}
