package cif;

import java.awt.image.BufferedImage;
import java.util.List;

import cif.convenience.Benchmark;
import cif.convenience.HelperUtils;
import cif.convenience.Unit;
import cif.core.PixelBlender;
import cif.core.PixelDataObject;
import cif.core.primary.PrimaryCompressor;
import cif.core.secondary.SecondaryCompressor;

public class Compressor {
	private String compressedData = "";
	
	public Compressor(BufferedImage image) {
		compressedData = compress(new PixelDataObject(image).getPixelData());
	}

	private String compress(List<List<Integer>> data) {
		HelperUtils.pixelData = data;
		HelperUtils.pixelData = HelperUtils.flipData(HelperUtils.pixelData);
		
		Benchmark.start(4);
		HelperUtils.pixelData = new PixelBlender(HelperUtils.pixelData).getBlendedData();
		Benchmark.endAndPrint(4, "Pixel blending took: ", Unit.MILLISECONDS);
		
		//primary compression
		Benchmark.start(6);
		String pCompressedData = new PrimaryCompressor(HelperUtils.pixelData).getPCompressedData();
		Benchmark.endAndPrint(6, "Primary compression took: ", Unit.MILLISECONDS);
		
		//secondary compression
		Benchmark.start(10);
		String sCompressedData = new SecondaryCompressor(pCompressedData).getSCompressedData();
		Benchmark.endAndPrint(10, "Secondary compressor took: ", Unit.MILLISECONDS);
		
		return sCompressedData;
	}
	
	public String getData() {
		return compressedData;
	}
}
