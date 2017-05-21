package cif.backend;

import cif.convenience.Benchmark;
import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileReader.ReadAs;
import cif.convenience.FileUtils.FileWriter.WriteAs;
import cif.convenience.Print;
import cif.convenience.Unit;
@SuppressWarnings("unused")
public class Main {
	private String input = "C:\\Users\\Alex K. Quilliam\\Desktop\\input.png";
	private String textOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.txt";
	private String pngOutput = "C:\\Users\\Alex K. Quilliam\\Desktop\\output.png";

	public Main() {
		double startingFileSize = FileUtils.getFileSize(input, Unit.KILOBYTES);
		Benchmark.start(0);
		new PrimaryCompressor((int[][]) FileUtils.FileReader.read(input, ReadAs.PIXELDATA), textOutput, 100);
		Benchmark.endAndPrint(0, "Compression took ", Unit.MILLISECONDS);
		Print.ln("File size is " + FileUtils.getFileSize(textOutput, Unit.KILOBYTES) + " kilobytes, down from " + startingFileSize + " kilobytes\n");
		Benchmark.start(1);
		FileUtils.FileWriter.write(pngOutput, new PrimaryDecompressor(textOutput).getPixelData(), WriteAs.PNG);
		Benchmark.endAndPrint(1, "Decompression took ", Unit.MILLISECONDS);
	}

	public static void main(String[] args) {
		new Main();
	}
}
