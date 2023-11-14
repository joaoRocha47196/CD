package repo;

//TODO verificar se o round robin está a funcionar corretamente
class RoundRobin {
    private int currentIndex;

    public RoundRobin() {
        this.currentIndex = 0;
    }

    public int getNextIndex(int listSize) {
        int res = currentIndex;
        currentIndex = (currentIndex + 1) % listSize;
        return res;
    }

    public int getNextIndex2(int listSize) {
        int res = currentIndex;
        currentIndex++;
        if(currentIndex == listSize)
            currentIndex = 0;
        return res;
    }

}