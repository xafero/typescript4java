package com.xafero.ts4j.core;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.xafero.ts4j.TypeScriptCompiler;

public class MemoryFS implements Closeable {

	public final String newLine = String.format("%n");

	private final TypeScriptCompiler compiler;
	private final Map<String, String> memory;
	private final String root;
	private final PrintStream out;
	private final PrintStream err;

	public MemoryFS(TypeScriptCompiler compiler) {
		this.compiler = compiler;
		this.memory = new LinkedHashMap<String, String>();
		this.root = "/" + "tmp";
		this.out = System.out;
		this.err = System.err;
	}

	public TypeScriptCompiler getCompiler() {
		return compiler;
	}

	public Map<String, String> getMemory() {
		return memory;
	}

	public void write(Object obj) {
		out.println(obj);
	}

	public void exit(int status) {
		err.println("Exit status => " + status);
	}

	public String getCurrentDirectory() {
		return root;
	}

	public String getExecutingFilePath() {
		return root + "/" + getClass().getSimpleName() + ".ts";
	}

	public boolean fileExists(String path) {
		return memory.containsKey(path);
	}

	public String readFile(String path) {
		return readFile(path, "UTF8");
	}

	public String readFile(String path, String encoding) {
		return memory.get(path);
	}

	public void readDirectory(String path, String extension, String exclude) {
		throw new UnsupportedOperationException(path + "#" + extension + "#" + exclude);
	}

	public void writeFile(String path, String data, boolean byteOrderMark) {
		memory.put(path, data);
	}

	public void writeFile(String path, String data) {
		writeFile(path, data, true);
	}

	public void push(String path, Reader reader) throws IOException {
		push(path, IOUtils.toString(reader));
	}

	public void push(String path, String text) {
		memory.put(path, text);
		memory.put(root + "/" + path, text);
	}

	@Override
	public void close() throws IOException {
		memory.clear();
	}
}