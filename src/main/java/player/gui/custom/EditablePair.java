package player.gui.custom;
/**
 * @author sainthxd@gmail.com
 */
public class EditablePair<K,V> {
	private K key;
	private V value;
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	public EditablePair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	
}
