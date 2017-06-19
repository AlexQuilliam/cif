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

//TODO:
//*In PrimaryPatternSet, find out why the limit can't be less than 6
//*In FileUtils, implement complete rotation functianality
//*Optimize PixelShifter
@SuppressWarnings("unused")
public class Main {
	private String input = "C:\\Users\\Alex K. Quilliam\\Desktop\\input.png";
	private String textOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.txt";
	private String pngOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.png";

	@SuppressWarnings("unchecked")
	public Main() {		
		GlobalUtils.pixelData = (List<List<Integer>>) FileUtils.FileReader.read(input, ReadAs.PIXELDATA);
		GlobalUtils.pixelData = GlobalUtils.flipData(GlobalUtils.pixelData);
		
		Benchmark.start(4);
		GlobalUtils.pixelData = new PixelBlender(GlobalUtils.pixelData).getBlendedData();
		Benchmark.endAndPrint(4, "Pixel blending took: ", Unit.MILLISECONDS);
		
		Benchmark.start(6);
		String pCompressionResult = new PrimaryCompressor(GlobalUtils.pixelData).getPCompressedData();
		Benchmark.endAndPrint(6, "Primary compression took ", Unit.MILLISECONDS);
			
		FileUtils.FileWriter.write(textOutput, pCompressionResult, WriteAs.PLAINTEXT);

		Benchmark.start(1);
		List<List<Integer>> decompressedPixelData = new PrimaryDecompressor(pCompressionResult).getPixelData();
		Benchmark.endAndPrint(1, "Primary decompression took ", Unit.MILLISECONDS);
		
		FileUtils.FileWriter.write(pngOutput, decompressedPixelData, WriteAs.PNG);
		
		Print.ln("File size is " + FileUtils.getFileSize(textOutput, Unit.KILOBYTES) + " kilobytes, down from " + FileUtils.getFileSize(pngOutput, Unit.KILOBYTES) + " kilobytes\n");
	}

	public static void main(String[] args) {
		new Main();
	}
}
