package at.fhooe.sail.vis.jaxb.json.main;

import jakarta.xml.bind.*;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JSON JAXB Project
 */
public class Main {

	/**
	 * Entry point of application
	 *
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("Hello XML");
		Pet pet = createPet();

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Pet.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(pet, sw);
			String jsonString = sw.toString();

			System.out.println(jsonString);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
			jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);

			StreamSource stream = new StreamSource(new StringReader(jsonString));
			JAXBElement<Pet> petContainer =jaxbUnmarshaller.unmarshal(stream, Pet.class);
			Pet newPet = petContainer.getValue();

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
