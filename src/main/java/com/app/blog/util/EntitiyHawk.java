package com.app.blog.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public class EntitiyHawk {

	public ResponseEntity<?> genericSuccess() {
		Map<String, Boolean> map = new HashMap<>();
		map.put("status", true);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericSuccess(Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", true);
		map.put("data", data);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericError() {
		Map<String, Boolean> map = new HashMap<>();
		map.put("status", false);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericError(Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", false);
		map.put("data", data);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericResponse(Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("data", data);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericResponse(boolean status) {
		Map<String, Boolean> map = new HashMap<>();
		map.put("status", status);
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> genericResponse(boolean status, Object data) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("data", data);
		return ResponseEntity.ok(map);
	}
}
