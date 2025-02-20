public class Statistics {
    public int total; // total of stroke occurred
    public int age;

    public Statistics(int total, int age)
    {
        this.total = total;
        this.age = age;
    }

    public int getAge() {
        return age;
    }


    public String toString()
    {
        return    "  < " + this.total
                + " |" + this.age +" > ";
    }
}
