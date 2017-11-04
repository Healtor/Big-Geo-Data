package MapReduce;

/**
 * Esta clase representa una region delimitada por dos puntos
 * @author: Jaime Pina Cambero
 * @version: 28/12/2016 
*/

public class Region {
	
	private Float LatPunto1;
	private Float LonPunto1;
	private Float LatPunto2;
	private Float LonPunto2;
	
	
	public Region(Float latPunto1, Float lonPunto1, Float latPunto2, Float lonPunto2) {
		super();
		LatPunto1 = latPunto1;
		LonPunto1 = lonPunto1;
		LatPunto2 = latPunto2;
		LonPunto2 = lonPunto2;
	}
	
	public Region() {
		super();

	}
	public Float getLatPunto1() {
		return LatPunto1;
	}
	public void setLatPunto1(Float latPunto1) {
		LatPunto1 = latPunto1;
	}
	public Float getLonPunto1() {
		return LonPunto1;
	}
	public void setLonPunto1(Float lonPunto1) {
		LonPunto1 = lonPunto1;
	}
	public Float getLatPunto2() {
		return LatPunto2;
	}
	public void setLatPunto2(Float latPunto2) {
		LatPunto2 = latPunto2;
	}
	public Float getLonPunto2() {
		return LonPunto2;
	}
	public void setLonPunto2(Float lonPunto2) {
		LonPunto2 = lonPunto2;
	}
	
	@Override
	public String toString() {
		System.out.println(LatPunto1 + "," + LonPunto1 + "y" + LatPunto2 + "," + LonPunto2);
		return super.toString();
	}
}
