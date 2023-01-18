package at.fhooe.sail.vis.jaxb.xml.main;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * XML JAXB Project
 */
public class Main {

	/**
	 * Entry point of application
	 * Creates new pet object, parse this object to json and back to a pet object
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("Hello XML");
		Pet pet = createPet();

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Pet.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(pet, sw);
			String xmlString = sw.toString();

			System.out.println(xmlString);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Pet newPet = (Pet)jaxbUnmarshaller.unmarshal(new StringReader(xmlString));

			System.out.println("Pet Objekt alt:");
			System.out.println(pet);
			System.out.println("Pet Objekt neu:");
			System.out.println(newPet);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates dummy pet-object
	 * @return created dummy object
	 */
	private static Pet createPet() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = sdf.parse("10.02.1940");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		String[] vaccinations = {"cat flu", "feline distemper", "rabies", "leucosis"};

		return new Pet("Thomas", "Tom", date, Type.CAT, vaccinations, "123456789");
	}
}
