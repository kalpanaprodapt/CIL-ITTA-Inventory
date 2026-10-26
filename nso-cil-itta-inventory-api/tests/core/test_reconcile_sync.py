from itta_core.jobs.reconcile_sync import parse_args


def test_defaults():
    args = parse_args(["--domain", "device"])
    assert args.domain == "device"
    assert args.page_size == 500
    assert args.apply is False


def test_apply_flag():
    assert parse_args(["--domain", "device", "--apply"]).apply is True
