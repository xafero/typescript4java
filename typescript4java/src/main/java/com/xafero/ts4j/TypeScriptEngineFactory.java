package com.xafero.ts4j;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * A {@code TypeScriptEngineFactory} is an implementation of
 * {@code ScriptEngineFactory} that generates {@code ScriptEngines} to run
 * TypeScript.
 *
 * @see javax.script.ScriptEngineFactory
 */
public class TypeScriptEngineFactory implements ScriptEngineFactory {

	private static final String ENGINE_NAME = "TypeScript";
	private static final String ENGINE_VERSION = "1.0.0";
	private static final List<String> EXTENSIONS = Arrays.asList("ts");
	private static final List<String> MIME_TYPES = Arrays.asList("application/typescript", "text/typescript");
	private static final List<String> NAMES = Arrays.asList("TypeScript", "typescript");
	private static final String LANGUAGE_NAME = "TypeScript";
	private static final String LANGUAGE_VERSION = "1.7.5";

	private ScriptEngineFactory jsFactory;

	@Override
	public String getEngineName() {
		return ENGINE_NAME;
	}

	@Override
	public String getEngineVersion() {
		return ENGINE_VERSION;
	}

	@Override
	public List<String> getExtensions() {
		return EXTENSIONS;
	}

	@Override
	public String getLanguageName() {
		return LANGUAGE_NAME;
	}

	@Override
	public String getLanguageVersion() {
		return LANGUAGE_VERSION;
	}

	@Override
	public List<String> getMimeTypes() {
		return MIME_TYPES;
	}

	@Override
	public List<String> getNames() {
		return NAMES;
	}

	private ScriptEngineFactory findJavaScriptFactory() {
		ScriptEngineManager mgr = new ScriptEngineManager();
		for (ScriptEngineFactory sef : mgr.getEngineFactories())
			if (sef.getLanguageName().equals("javascript"))
				return sef;
		throw new UnsupportedOperationException("Couldn't find JS engine factory!");
	}

	@Override
	public ScriptEngine getScriptEngine() {
		if (jsFactory == null)
			jsFactory = findJavaScriptFactory();
		ScriptEngine js = jsFactory.getScriptEngine();
		TypeScriptCompiler compiler = TypeScriptCompiler.create(js);
		return new TypeScriptEngine(this, js, compiler);
	}

	@Override
	public Object getParameter(String key) {
		if (key.equals(ScriptEngine.ENGINE))
			return getEngineName();
		if (key.equals(ScriptEngine.ENGINE_VERSION))
			return getEngineVersion();
		if (key.equals(ScriptEngine.NAME))
			return getNames().get(0);
		if (key.equals(ScriptEngine.LANGUAGE))
			return getLanguageName();
		if (key.equals(ScriptEngine.LANGUAGE_VERSION))
			return getLanguageVersion();
		return jsFactory.getParameter(key);
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		String txt = Arrays.toString(args);
		return String.format("%s.%s(%s))", obj, m, txt.substring(1, txt.length() - 2));
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		return "print(" + toDisplay + ")";
	}

	@Override
	public String getProgram(String... statements) {
		StringBuilder bld = new StringBuilder();
		for (String stat : statements)
			bld.append(String.format("%s%n", stat));
		return bld.toString();
	}
}