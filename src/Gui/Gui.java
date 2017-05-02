package Gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import Cache.RList;
import Cache.RMap;
import Cache.RString;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Gui implements FrontEnd {
	private static final Gson GSON = new Gson();
	private static final RString rString = new RString();
	private static final RList rList = new RList();
	private static final RMap rMap = new RMap();

	private static FreeMarkerEngine createEngine() {
		Configuration config = new Configuration();
		File templates = new File("resources/freemarker/template");
		try {
			config.setDirectoryForTemplateLoading(templates);
		} catch (IOException ioe) {
			System.err.printf("ERROR: Unable use %s for template loading.%n", templates);
			System.exit(1);
		}
		return new FreeMarkerEngine(config);
	}

	public void runSparkServer(int port) {
		Spark.port(port);
		Spark.externalStaticFileLocation("resources/static");
		Spark.exception(Exception.class, new ExceptionPrinter());

		FreeMarkerEngine freeMarker = createEngine();
		// Setup Spark Routes
		Spark.get("/redis", new MiniRedisHandler(), freeMarker);
		Spark.post("/results", new ResultsHandler());
	}

	private static Object processString(String word, String command) {
		Object toReturn = null;
		switch (command) {
		case "GET":
			toReturn = rString.get(word);
			System.out.println("get: " + toReturn);
			break;
		case "SET":
			String[] arr = word.split(",");
			if (arr.length > 1) {
				toReturn = rString.set(arr[0], arr[arr.length - 1]);
			} else {
				toReturn = "#error";
			}
			System.out.println("set: " + toReturn);
			break;
		case "DELETE":
			toReturn = rString.delete(word);
			break;
		case "SEARCH-KEYS":
			toReturn = rString.searchKeys(word);
			System.out.println("search: " + toReturn);
			break;
		case "GET-KEYS":
			toReturn = rString.getAllKeys();
			System.out.println(toReturn);
			break;
		default:
			break;
		}
		return toReturn;
	}

	private static Object processList(String word, String command) {
		Object toReturn = null;
		switch (command) {
		case "GET":
			toReturn = rList.get(word);
			System.out.println("getlist: " + toReturn);
			break;
		case "SET":
			String[] arr = word.split(",");
			if (arr.length > 1) {
				String val = "";
				for (int i = 1; i < arr.length - 1; i++) {
					val += arr[i] + ",";
				}
				val += arr[arr.length - 1];
				toReturn = rList.set(arr[0], val);
				System.out.println("val: " + val);
			} else {
				toReturn = "#error";
			}
			System.out.println("setlist: " + toReturn);
			break;
		case "DELETE":
			toReturn = rList.delete(word);
			break;
		case "SEARCH-KEYS":
			toReturn = rList.searchKeys(word);
			System.out.println("searchlist: " + toReturn);
			break;
		case "GET-KEYS":
			toReturn = rList.getAllKeys();
			System.out.println(toReturn);
			break;
		case "APPEND":
			String[] array = word.split(",");
			if (array.length > 1) {
				rList.append(array[0], array[array.length - 1]);
			} else {
				toReturn = "#error";
			}
			System.out.println("append: " + toReturn);
			break;
		case "POP":
			toReturn = rList.pop(word);
			System.out.println("pop: " + toReturn);
			break;
		default:
			break;
		}
		return toReturn;
	}

	private static Object processMap(String word, String command) {
		Object toReturn = null;
		switch (command) {
		case "GET":
			toReturn = rMap.get(word);
			System.out.println("getmap: " + toReturn);
			break;
		case "SET":
			String[] arr = word.split(",");
			for (String s : arr) {
				System.out.println(s);
			}
			if (arr.length > 2) {
				String val = arr[arr.length - 2] + "," + arr[arr.length - 1];
				System.out.println(arr[0]);
				System.out.println(val);
				toReturn = rMap.set(arr[0], val);
			} else {
				toReturn = "#error";
			}
			System.out.println("setmap: " + toReturn);
			break;
		case "DELETE":
			toReturn = rMap.delete(word);
			break;
		case "SEARCH-KEYS":
			toReturn = rMap.searchKeys(word);
			System.out.println("searchmap: " + toReturn);
			break;
		case "GET-KEYS":
			toReturn = rMap.getAllKeys();
			System.out.println(toReturn);
			break;
		case "MAPGET":
			String[] array = word.split(",");
			if (array.length > 1) {
				toReturn = rMap.mapGet(array[0], array[array.length - 1]);
			} else {
				toReturn = "#error";
			}
			System.out.println("mapget: " + toReturn);
			break;
		case "MAPSET":
			String[] buff = word.split(",");
			if (buff.length > 2) {
				rMap.mapSet(buff[0], buff[buff.length - 2], buff[buff.length - 1]);
			} else {
				toReturn = "#error";
			}
			System.out.println("mapset: " + toReturn);
			break;
		case "MAPDELETE":
			String[] buffer = word.split(",");
			if (buffer.length > 1) {
				rMap.mapDelete(buffer[0], buffer[buffer.length - 1]);
			} else {
				toReturn = "#error";
			}
			System.out.println("mapdelete: " + toReturn);
			break;
		default:
			break;
		}
		return toReturn;

	}

	private static class MiniRedisHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			Map<String, Object> variables = ImmutableMap.of("title", "Mini-Redis-Server");
			return new ModelAndView(variables, "Redis.ftl");
		}
	}

	private static class ResultsHandler implements Route {

		@Override
		public String handle(Request request, Response response) throws Exception {
			QueryParamsMap qm = request.queryMap();
			String word = qm.value("word");
			String command = qm.value("command");
			String data = qm.value("data");
			Object toReturn = null;
			switch (data) {
			case "string":
				toReturn = processString(word, command);
				break;
			case "list":
				toReturn = processList(word, command);
				break;
			case "map":
				toReturn = processMap(word, command);
				break;
			default:
				break;
			}
			if (toReturn == null) {
				toReturn = new ArrayList<>();
			}
			Map<String, Object> variables = new ImmutableMap.Builder<String, Object>().put("returned", toReturn)
					.build();
			return GSON.toJson(variables);
		}
	}

	private static class ExceptionPrinter implements ExceptionHandler {
		@Override
		public void handle(Exception e, Request req, Response res) {
			res.status(500);
			StringWriter stacktrace = new StringWriter();
			try (PrintWriter pw = new PrintWriter(stacktrace)) {
				pw.println("<pre>");
				e.printStackTrace(pw);
				pw.println("</pre>");
			}
			res.body(stacktrace.toString());

		}
	}

}
