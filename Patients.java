public class Patients {
    public int id;
    public int age;

    public Patients(int id, int age)
    {
        this.id = id;
        this.age = age;
    }

    public String toString()
    {
        return    "  <Id: " + this.id
                + " |" + this.age + "> ";
    }
}
