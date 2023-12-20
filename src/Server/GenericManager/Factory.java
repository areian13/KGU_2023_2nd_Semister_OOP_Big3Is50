package Server.GenericManager;

public interface Factory <T extends Data> {
    T create();
}