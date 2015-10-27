package wiii.awa

import java.util.UUID

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

object UUIDProtocol extends DefaultJsonProtocol {
    implicit object UUIDJsonProtocol extends RootJsonFormat[UUID] {
        def write(uuid: UUID) = JsString(uuid.toString)
        def read(js: JsValue) = js match {
            case JsString(str) => UUID.fromString(str)
            case _ => throw new IllegalArgumentException("not a uuid")
        }
    }
}

object WebHookProtocol extends DefaultJsonProtocol {
    import UUIDProtocol.UUIDJsonProtocol
    implicit val hookConfig = jsonFormat6(HookConfig.apply)
    implicit val hookConfigOpt = jsonFormat6(HookConfigOpt.apply)
    implicit val requestFormat = jsonFormat2(HookSubscription)
    implicit val unsubscribe = jsonFormat1(HookUnsubscribe)
}
