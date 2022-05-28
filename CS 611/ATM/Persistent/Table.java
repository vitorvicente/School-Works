package Persistent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Table<T extends Traceable> implements Serializable {

	private static final long serialVersionUID = 500L;

	final HashMap<Long, T> map;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Table() {
		map = new HashMap<>();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void add(T obj) {
		map.put(obj.getId(), obj);
	}

	public void addAll(List<T> list) {
		list.forEach(t -> map.put(t.getId(), t));
	}

	public T get(long id) {
		return map.get(id);
	}

	public ArrayList<T> getAll() {
		return new ArrayList<>(map.values());
	}

	public boolean containsKey(long id) {
		return map.containsKey(id);
	}

	public boolean remove(long id) {
		return map.remove(id) != null;
	}

	public long getNextId() {
		Random random = new Random();
		long id = random.nextLong();
		while (id < 0 || containsKey(id)) {
			id = random.nextLong();
		}
		return id;
	}

}
