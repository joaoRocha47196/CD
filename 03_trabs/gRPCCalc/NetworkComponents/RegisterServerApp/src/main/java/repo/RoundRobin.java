package repo;

//TODO verificar se o round robin está a funcionar corretamente
class RoundRobin {
    private int currentIndex;

    public RoundRobin() {
        this.currentIndex = 0;
    }

    public int getNextIndex(int listSize) {
        int nextIndex = currentIndex;
        currentIndex = (currentIndex + 1) % listSize;
        return nextIndex;
    }

    public void updateState(int newIndex) {
        currentIndex = newIndex;
    }
}
