package com.alexdupre.fedex.ship

package object models {

  type AccountNumber                             = PartyAccountNumber
  type BaseProcessOutputVO                       = CancelTagOuputVO
  type BaseProcessOutputVOCancelShipment         = CancelShipmentOutputVO
  type BaseProcessOutputVOCreateTag              = CreateTagOutputVO
  type BaseProcessOutputVOGetOpenShipmentResults = GetOpenShipmentResultsOutputVO
  type BaseProcessOutputVOShipShipment           = ShipShipmentOutputVO
  type BaseProcessOutputVOValidate               = VerifyShipmentOutputVO
  type CompletedTagDetail                        = CompletedTagDetail2
  type CreateTagRequestedShipment                = RequestedShipment
  type ShipmentAccountNumber                     = PartyAccountNumber2
}
