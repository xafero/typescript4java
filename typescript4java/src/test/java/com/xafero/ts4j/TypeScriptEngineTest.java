package com.xafero.ts4j;

import static org.junit.Assert.assertEquals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class TypeScriptEngineTest {

	@Test
	public void testEngine() throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine eng = mgr.getEngineByExtension("ts");
		assertEquals(42 + "", eng.eval("35+7") + "");
	}
}