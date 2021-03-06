// Copyright (C) 2014 Fehmi Can Saglam (@fehmicans) and contributors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package reactivemongo.extensions.json.dao

import org.joda.time.DateTime
import org.scalatest._
import org.scalatest.concurrent._
import org.scalatest.time.SpanSugar._
import reactivemongo.extensions.json.model.TemporalModel
import reactivemongo.extensions.json.model.TemporalModel._

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TemporalModelJsonDaoSpec
    extends AnyFlatSpec
    with Matchers
    with ScalaFutures
    with BeforeAndAfter
    with OneInstancePerTest {

  implicit override def patienceConfig = PatienceConfig(timeout = 20 seconds, interval = 1 seconds)

  val dao = new TemporalModelJsonDao

  after {
    dao.dropSync()
  }

  "A TemporalModelJsonDao" should "update updateAt" in {
    val temporalModel = TemporalModel(name = "foo", surname = "bar")

    val futureResult = for {
      _                  <- dao.insert(temporalModel)
      maybeTemporalModel <- dao.findById(temporalModel._id)
    } yield maybeTemporalModel

    whenReady(futureResult) { maybeTemporalModel =>
      maybeTemporalModel should be(Symbol("defined"))
      maybeTemporalModel.get._id shouldBe temporalModel._id
      maybeTemporalModel.get.name shouldBe temporalModel.name
      maybeTemporalModel.get.surname shouldBe temporalModel.surname
      maybeTemporalModel.get.createdAt shouldBe temporalModel.createdAt
      maybeTemporalModel.get.updatedAt.isAfter(temporalModel.updatedAt) shouldBe true
    }
  }

}
