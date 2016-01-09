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
		String ts = tsc.compile("class Test { name: string }").split("//")[0];
		assertEquals("var Test = (function () {    function Test() {    }    return Test;})();", strip(ts));
	}

	private static String strip(String text) {
		return text.replace('\n' + "", "").replace('\r' + "", "").replace('\t' + "", "").trim();
	}
}