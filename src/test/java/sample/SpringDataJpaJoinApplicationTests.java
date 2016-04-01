package sample;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataJpaJoinApplication.class)
@Transactional
public class SpringDataJpaJoinApplicationTests {

	@Autowired
	PersonRepository people;
	@Autowired
	SignatureRepository signatures;

	Person parent;

	Person child;

	/**
	 * Setup data that emulates a parent has signed for the child.
	 *
	 * <pre>
	 * ================================
	 * signature table
	 * ================================
	 *
	 * name
	 * -------------
	 * dad
	 *
	 * ================================
	 * person table
	 * ================================
	 *
	 * name          parent
	 * ----------------------------
	 * dad
	 * child         dad
	 * </pre>
	 */
	@Before
	public void parentSignedForChild() {
		parent = new Person();
		parent.setName("dad");

		parent = people.save(parent);

		child = new Person();
		child.setName("child");
		child.setParent(parent);

		child = people.save(child);

		Signature parentSigned = new Signature();
		parentSigned.setPerson(parent);

		parentSigned = signatures.save(parentSigned);
	}

	/**
	 * Has the parent signed an agreement?
	 */
	@Test
	public void findSignatureByParentName() {
		assertThat(signatures.findSignature(parent.getName())).isNotNull();
	}

	/**
	 * Did this child sign for them self or did the parent sign for the child?
	 *
	 * This test fails but should work
	 * {@link SignatureRepository#findSignature(String)} needs fixed
	 */
	@Test
	public void findSignatureByChildName() {
		assertThat(signatures.findSignature(child.getName())).isNotNull();
	}

}