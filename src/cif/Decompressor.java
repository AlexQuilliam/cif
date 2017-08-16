package cif;

import java.awt.image.BufferedImage;
import java.util.List;

import cif.convenience.Benchmark;
import cif.convenience.FileUtils;
import cif.convenience.FileUtils.FileWriter.WriteAs;
import cif.convenience.ImageUtils;
import cif.convenience.Unit;
import cif.core.primary.PrimaryDecompressor;
import cif.core.secondary.SecondaryDecompressor;

public class Decompressor {
	private BufferedImage image = null;

	public Decompressor(String compressedData) {
		image = decompress(compressedData);
	}

	private BufferedImage decompress(String compressedData) {	
		//secondary decompression
		Benchmark.start(11);
		String sDecompressedData = new SecondaryDecompressor(compressedData).getSDecompressedData();
		Benchmark.endAndPrint(11, "Secondary decompressor took: ", Unit.MILLISECONDS);
		
		FileUtils.FileWriter.write("C:\\Users\\Alex K. Quilliam\\Desktop\\dbg.txt", compressedData, WriteAs.PLAINTEXT);
				
		//primary decompression
		Benchmark.start(1);
		List<List<Integer>> decompressedPixelData = new PrimaryDecompressor(sDecompressedData).getPixelData();
		Benchmark.endAndPrint(1, "Primary decompression took: ", Unit.MILLISECONDS);
		
		return ImageUtils.convertPixelData(decompressedPixelData);
	}
	
	public BufferedImage getData() {
		return image;
	}
}
