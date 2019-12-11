package fun.wackloner.transferwiser.model;

public class Account {
    private long id;
    private String name;

    public Account(long id) {
        this.id = id;
    }

    public Account() {}

    public long getId() {
        return id;
    }
}
