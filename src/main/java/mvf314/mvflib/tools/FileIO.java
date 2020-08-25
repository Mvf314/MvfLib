package mvf314.mvflib.tools;

import net.minecraft.data.DirectoryCache;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static net.minecraft.data.IDataProvider.HASH_FUNCTION;

/**
 * FileIO contains methods regarding writing/reading files to and from disk.
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.5
 */
public class FileIO {
	/**
	 * Save a string to disk
	 * @param cache Directory cache
	 * @param s File contents to save
	 * @param pathIn File path
	 * @throws IOException thrown if pathIn cant be accessed
	 */
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

