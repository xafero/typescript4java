package com.xafero.ts4j;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class TypeScriptCompilerTest {

	@Test
	public void testCompiler() throws ScriptException, IOException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		TypeScriptEngine eng = (TypeScriptEngine) mgr.getEngineByExtension("ts");
		TypeScriptCompiler tsc = eng.getCompiler();
		String ts = tsc.compile("class Test { name: string }");
		assertEquals(42.0, ts);
	}
}