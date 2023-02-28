package bitmap;

public class BitMap2 {
    private int[] bits;

    public BitMap2(int max) {
        bits = new int[(max + 32) / 32];
    }

    public void add(int num) {
        bits[num / 32] |= 1 << (num % 32);
    }

    public void delete(int num) {
        bits[num / 32]  &= ~(1 << (num % 32));
    }

    public boolean contains(int num) {
        return (bits[num / 32] & (1 << (num % 32))) != 0;
    }
}
