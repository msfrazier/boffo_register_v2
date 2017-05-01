package product;   
public class Rating{
        protected String name = "";
        protected int age = 0;

        public Rating (String _name, int _age){
            this.name = _name;
            this.age = _age;
        }
        @Override
        public String toString(){
            return this.name;
        }
}
