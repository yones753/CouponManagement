package JavaBeans;

public enum Category {
  
	
	
	Food,
	Electricity,
	Restaurant,
	Vacation;
	
	public static int categoryToInt(Category category) {
		int categoryValue = -1;

		switch (category) {
		case Food:
			categoryValue = 1;
			break;
		case Electricity:
			categoryValue = 2;
			break;
		case Restaurant:
			categoryValue = 3;
			break;
		case Vacation:
			categoryValue = 4;
			break;
		}
		return categoryValue;
	}
		
	
	public static Category intToCategory(int num) {
		Category categoryValue =Food;
		
		switch(num) {
			case 1:
				categoryValue = Food;
				break;
			case 2:
				categoryValue = Electricity;
				break;
			case 3:
				categoryValue = Restaurant;
				break;
			case 4:
				categoryValue = Vacation;
				break;	
		}
		return categoryValue;
	}


	
	
}


