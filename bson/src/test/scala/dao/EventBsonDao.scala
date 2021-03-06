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

package reactivemongo.extensions.dao

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.extensions.model.Event
import reactivemongo.api.DB
import reactivemongo.extensions.dsl.BsonDsl

class EventBsonDao(_db: Future[DB]) extends BsonDao[Event, String](_db, "events") with BsonDsl {

  def findByTitle(title: String): Future[Option[Event]] = findOne("title".$eq(title))

  def dropDatabaseSync(): Unit = Await.result(_db.map(_.drop()), 20 seconds)
}
