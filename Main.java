public class Main {
    public class Person{
        private int age;
        private String name;
        public Person() {
            this.age = 18;
            this.name = "Nho";
        }
        public int getAge() {
            return age;
        }
        public String getName() {
            return name;
        }
        public void setAge(int age){
            this.age = age;
        }
        public void setName(String name){
            this.name = name;
        }
        public void talk(){
            System.out.println( getName() + " nho nguoi yeu cu vai lon");
        }

    }

    public void main(String[] args) {
        Person p = new Person();
        p.setAge(20);
        p.setName("Vi Minh Hien");
        p.talk();
    }
    
}
