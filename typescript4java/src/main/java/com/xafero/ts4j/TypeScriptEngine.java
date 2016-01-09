package com.xafero.ts4j;

import java.io.IOException;
import java.io.Reader;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

/**
 * A {@code TypeScriptEngine} is an implementation of JSR-223 for TypeScript.
 *
 * @see javax.script.ScriptEngine
 * @see javax.script.Compilable
 * @see javax.script.Invocable
 */
public class TypeScriptEngine extends AbstractScriptEngine implements ScriptEngine, Compilable, Invocable {

	private final TypeScriptEngineFactory factory;
	private final ScriptEngine js;
	private final TypeScriptCompiler compiler;

	public TypeScriptEngine(TypeScriptEngineFactory factory, ScriptEngine js, TypeScriptCompiler compiler) {
		this.factory = factory;
		this.js = js;
		this.compiler = compiler;
	}

	public TypeScriptCompiler getCompiler() {
		return compiler;
	}

	public String toJavaScript(String script) throws ScriptException {
		try {
			String conv = compiler.compile(script);
			return conv;
		} catch (IOException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public String toJavaScript(Reader reader) throws ScriptException {
		try {
			String conv = compiler.compile(reader);
			return conv;
		} catch (IOException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public Bindings createBindings() {
		return js.createBindings();
	}

	@Override
	public Object eval(String script, ScriptContext context) throws ScriptException {
		return js.eval(toJavaScript(script), context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context) throws ScriptException {
		return js.eval(toJavaScript(reader), context);
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return factory;
	}

	@Override
	public CompiledScript compile(String script) throws ScriptException {
		return ((Compilable) js).compile(toJavaScript(script));
	}

	@Override
	public CompiledScript compile(Reader script) throws ScriptException {
		return ((Compilable) js).compile(toJavaScript(script));
	}

	@Override
	public Object invokeMethod(Object obj, String name, Object... args) throws ScriptException, NoSuchMethodException {
		return ((Invocable) js).invokeMethod(obj, name, args);
	}

	@Override
	public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
		return ((Invocable) js).invokeFunction(name, args);
	}

	@Override
	public <T> T getInterface(Class<T> clazz) {
		return ((Invocable) js).getInterface(clazz);
	}

	@Override
	public <T> T getInterface(Object obj, Class<T> clazz) {
		return ((Invocable) js).getInterface(obj, clazz);
	}
}