package kcomp.chat.common.listeners;

public interface Listener<T> {

	public void listen(Object payload);

	public void setListener(T listener);

}
