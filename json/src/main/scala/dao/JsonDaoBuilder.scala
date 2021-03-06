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

import play.api.libs.json.OFormat
import play.api.libs.json.Writes
import reactivemongo.api.DB
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter}
import reactivemongo.extensions.dao.LifeCycle
import reactivemongo.extensions.dao.ReflexiveLifeCycle

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class JsonDaoBuilder[Model, ID: Writes](db: => Future[DB]) {
  def apply(collectionName: String)(
      implicit fmt: OFormat[Model],
      modelReader: BSONDocumentReader[Model],
      modelWriter: BSONDocumentWriter[Model],
      lifeCycle: LifeCycle[Model, ID] = new ReflexiveLifeCycle[Model, ID],
      ec: ExecutionContext
  ): JsonDao[Model, ID] = {
    JsonDao(db, collectionName)
  }
}

object JsonDaoBuilder {
  def apply[Model, ID: Writes](db: => Future[DB]): JsonDaoBuilder[Model, ID] = {
    new JsonDaoBuilder[Model, ID](db)
  }
}
