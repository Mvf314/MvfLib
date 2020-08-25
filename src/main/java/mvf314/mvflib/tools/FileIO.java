package mvf314.mvflib.tools;

import net.minecraft.data.DirectoryCache;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static net.minecraft.data.IDataProvider.HASH_FUNCTION;

public class FileIO {
	// Changed version from IDataProvider#save
	public static void save(DirectoryCache cache, String s, Path pathIn) throws IOException {
		String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
		if (!Objects.equals(cache.getPreviousHash(pathIn), s1) || !Files.exists(pathIn)) {
			Files.createDirectories(pathIn.getParent());

			try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pathIn)) {
				bufferedwriter.write(s);
			}
		}
		cache.recordHash(pathIn, s1);
	}
}

