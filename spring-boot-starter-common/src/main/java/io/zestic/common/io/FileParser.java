package io.zestic.common.io;

import java.io.File;
import java.util.List;

public interface FileParser {

    List<List<String>> parse(final File file);
}
