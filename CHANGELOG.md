#Changelog

# 2.1.0 - 2016-07-06

* Add sources to maven repository (#14).
* Add `webhookUrl` to create payment (#15).

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
