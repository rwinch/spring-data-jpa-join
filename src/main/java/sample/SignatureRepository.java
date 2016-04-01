/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.mysema.query.types.Predicate;

/**
 * @author Rob Winch
 *
 */
public interface SignatureRepository extends CrudRepository<Signature, Long>, QueryDslPredicateExecutor<Signature> {

	/**
	 *
	 * @param name either the name of the child or the parent of this child might have signed
	 * @return
	 */
	// FIXME
	default Signature findSignature(String name) {
		Predicate p = QSignature.signature.person.name.eq(name);
		// prefer to do join and NOT a select in clause
		return findOne(p);
	}
}
