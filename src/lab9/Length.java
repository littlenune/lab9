package lab9;
/*
 * @author Tunya Wittayasiripaiboon 5810546676
 * The length that can be used.
 */
public enum Length implements Unit {
	METER("Meter",1.0),
	KILOMETER("Kilometer",1000.0),
	CENTIMETER("Centimeter",0.01),
	MILLIMETER("Millimeter",0.001),
	MILE("Mile",1609.344),
	FOOT("Foot",0.3048),
	WA("Wa",2.0);
	public final String name;
	public final double value;
	/*
	 * Creates a new length with the given name and value.
	 * @param name of the length.
	 * 		  value of the length.
	 */
	Length (String name ,double value){
		this.name = name;
		this.value = value;
	}
	/*
	 * Implements from Unit interface get value of the length.
	 * @return value of the length.
	 */
	public double getValue(){
		return value;
	}
	/*
	 * Implements from Unit interface get name of the length.
	 * @return name of the length.
	 */
	public String toString (){
		return name;
	}	
}