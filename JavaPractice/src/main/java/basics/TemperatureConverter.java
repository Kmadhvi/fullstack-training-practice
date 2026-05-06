package basics;

public class TemperatureConverter {

	public static void main(String[] args) {
		double celsius = 37.0;
		double fahrenheit = (celsius * 9 / 5) + 32;

		double temperatureInFahrenheit = 98.6;
		double convertedCelsius = (temperatureInFahrenheit - 32) * 5 / 9;

		System.out.println(celsius + " degree Celsius = " + fahrenheit + " degree Fahrenheit");
		System.out.println(temperatureInFahrenheit + " degree Fahrenheit = " + convertedCelsius + " degree Celsius");

	}

}
