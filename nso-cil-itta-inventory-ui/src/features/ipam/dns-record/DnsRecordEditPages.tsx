import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { DnsRecordForm } from './DnsRecordForm';
import { useCreateDnsRecord, useUpdateDnsRecord, useDnsRecord } from './hooks';
import type { DnsRecordInput } from './types';

export function DnsRecordCreatePage() {
  const navigate = useNavigate();
  const create = useCreateDnsRecord();
  const [error, setError] = useState<string>();

  function onSubmit(input: DnsRecordInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/ipam/dns-records/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="dns-record-new-title">
      <PageHeader id="dns-record-new-title" title="New Dns Record" />
      <DnsRecordForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/ipam/dns-records')}
      />
    </section>
  );
}

export function DnsRecordEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useDnsRecord(id);
  const update = useUpdateDnsRecord(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Dns Record" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Dns Record." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: DnsRecordInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/ipam/dns-records/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="dns-record-edit-title">
      <PageHeader id="dns-record-edit-title" title={`Edit ${query.data.code}`} />
      <DnsRecordForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/ipam/dns-records/${id}`)}
      />
    </section>
  );
}
