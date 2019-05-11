package ar.edu.unlam.tallerweb1.persistencia;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Ciudad;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;

public class TestTp extends SpringTest{

	@Test
	@Transactional
	@Rollback(true)
	public void testPaisesHablaInlgesa() {
		Pais pais=new Pais("argentina","español");
		Pais pais1=new Pais("yanke","ingles");
		Pais pais2=new Pais("canada","inlges");
		getSession().save(pais2);
		getSession().save(pais1);
		getSession().save(pais);
		
		List <Pais> miListaPais= getSession().createCriteria(Pais.class)
				.add(Restrictions.like("idioma", "ingles"))
				.list();
				
		for(Pais p:miListaPais) {
			assertThat(p.getIdioma()).isEqualTo("ingles");
		}
		
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testPaisesDelContinenteEuropeo() {
		Continente miEuropa=new Continente("europa");
		Continente miAmerica=new Continente("america");
		Pais pais1 =new Pais("francia","frances");
		Pais pais2 =new Pais("españa","español");
		Pais pais3 =new Pais("brasil","portugues");
		pais1.setContinente(miEuropa);
		pais2.setContinente(miEuropa);
		pais3.setContinente(miAmerica);
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		List <Pais> miListaPais= getSession().createCriteria(Pais.class)
				.createAlias("continente", "continentejoin")
				.add(Restrictions.like("continentejoin.nombre","europa"))
				.list();
		for(Pais p:miListaPais) {
			
			assertThat(p.getContinente().getNombre()).isEqualTo("europa");
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testPaisesConCapitalAlNorteDelTropicoDeCancer() {
		Pais pais1 =new Pais("argentina");
		Pais pais2 =new Pais("agipto");
		Pais pais3 =new Pais("francia");
		Ciudad BuenosAires=new Ciudad("Buenos Aires");
		Ciudad Paris=new Ciudad("paris");
		Ciudad ElCairo=new Ciudad("El Cairo");
		Ubicacion ubiCairo=new Ubicacion(30.06,31.24);
		Ubicacion ubiBuenosAires=new Ubicacion(-34.60,-58.37);
		Ubicacion ubiParis=new Ubicacion(48.86,2.33);
		BuenosAires.setUbicacionGeografica(ubiBuenosAires);
		Paris.setUbicacionGeografica(ubiParis);
		ElCairo.setUbicacionGeografica(ubiCairo);
		pais1.setCapital(BuenosAires);
		pais2.setCapital(ElCairo);
		pais3.setCapital(Paris);
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		List<Pais> miListaPais=getSession().createCriteria(Pais.class)
				.createAlias("capital", "capitaljoin")
				.createAlias("capitaljoin.ubicacionGeografica","ubicaionjoin")
				.add(Restrictions.ge("ubicaionjoin.latitud", 23.26))
				.list();
			assertThat(miListaPais.size()).isEqualTo(2);
				
		
		}
	@Test
	@Transactional
	@Rollback(true)
	public void testQueBuscaCiudadesDelHemisferioSur() {
		Ciudad BuenosAires=new Ciudad("Buenos Aires");
		Ciudad Asuncion=new Ciudad("Asuncion");
		Ciudad ElCairo=new Ciudad("El Cairo");
		Ubicacion ubiCairo=new Ubicacion(30.06,31.24);
		Ubicacion ubiBuenosAires=new Ubicacion(-34.60,-58.37);
		Ubicacion ubiAsuncion=new Ubicacion(-25.28,-57.33);
		BuenosAires.setUbicacionGeografica(ubiBuenosAires);
		Asuncion.setUbicacionGeografica(ubiAsuncion);
		ElCairo.setUbicacionGeografica(ubiCairo);
		getSession().save(BuenosAires);
		getSession().save(Asuncion);
		getSession().save(ElCairo);
		List<Ciudad> listaCiudad=getSession().createCriteria(Ciudad.class)
				.createAlias("ubicacionGeografica", "ubicacionjoin")
				.add(Restrictions.lt("ubicacionjoin.latitud",0.00))
				.list();
		assertThat(listaCiudad.size()).isEqualTo(2);
	
}

}
