#Changelog

# 2.7.0 - 2017-12-09

* #50 add support for cancel payment
* #48 Unrecognized field "canBeCancelled"

# 2.6.2 - 2017-03-30

* Ignore `issuer` property for `Payment`

# 2.6.1 - 2017-03-14

* Ignore `resource` property for `Method` ([#46](https://github.com/stil4m/mollie-api/issues/46)).

# 2.6.0 - 2017-01-26

* Add `countryCode` to `Payment` model ([#45](https://github.com/stil4m/mollie-api/issues/45)).

# 2.5.0 - 2017-01-16

* Add `signatureDate` to `Mandate` model.

# 2.4.0 - 2016-11-18

* Add the notion of shared concepts that make the code more extendable (#40).
* Add API calls for mandates.

Thanks to @tubbynl for his contributions!

# 2.3.0 - 2016-11-13

* Merge CreatedPayment with payment #36.
* Integration tests for recurring payments #32)
* More forgiving ObjectMapper by default. It is less likely that the library breaks when Mollie releases a 'breaking' change. #25
* Code improvements (test/code location).

Thanks to @tubbynl for his contributions!


# 2.2.0 - 2016-10-27

* Add concept of customer and customer payments to support recurring payments (#19)
* Cleanup and test improvements

Thanks to @jcassee and @tubbynl for their contributions!

# 2.1.0 - 2016-07-06

* Add sources to maven repository (#14).
* Add `webhookUrl` to create payment (#15).

Thanks to @jcassee for his contributions to this library.

# 2.0.0 - 2016-05-31

* Change created payment detail to map (#10)

Thanks to @jvanoosterom for his contribution

# 1.5.0 - 2016-05-12

* Add concrete payment types (#9)

# 1.4.0 - 2016-01-25

* Add documentation (#8)
* Remove deprecated code from version 1.2.0

# 1.3.0 - 2016-01-18

* Add refunds api (#7)
* Add issuers api (#6)
* Add methods api (#5)

# 1.2.0 - 2016-01-11

* Rename `PaymentStatus` to more convenient name `Payment`.
* Add support for pagination #4
* Major structural refactoring and deprectated some methods.

# 1.1.0 - 2016-01-10

* Add verification when requesting payment without an valid identifier (#2, #3)

# 1.0.1 - 2016-01-05

* Added missing fields to payment status
  * locale
  * amountRefunded
  * amountRemaining
