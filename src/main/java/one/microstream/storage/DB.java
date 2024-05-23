package one.microstream.storage;

import java.net.URL;
import java.util.Optional;

import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.store.storage.restservice.types.StorageRestService;
import org.eclipse.store.storage.restservice.types.StorageRestServiceResolver;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;


public class DB
{
	public static EmbeddedStorageManager	storageManager;
	public final static DataRoot			root	= new DataRoot();
	
	static
	{
		ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
		Optional<URL> resource = loader.getResource("microstream.xml");
		
		storageManager = EmbeddedStorageConfiguration.load(
			resource.get().getPath()).createEmbeddedStorageFoundation().createEmbeddedStorageManager(root).start();
		
		// create the REST service
		StorageRestService service = StorageRestServiceResolver.resolve(storageManager);
		
		// and start it
		service.start();
	}
}
