import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { OspfAreaForm } from './OspfAreaForm';
import { useCreateOspfArea, useUpdateOspfArea, useOspfArea } from './hooks';
import type { OspfAreaInput } from './types';

export function OspfAreaCreatePage() {
  const navigate = useNavigate();
  const create = useCreateOspfArea();
  const [error, setError] = useState<string>();

  function onSubmit(input: OspfAreaInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/switching/ospf-areas/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="ospf-area-new-title">
      <PageHeader id="ospf-area-new-title" title="New Ospf Area" />
      <OspfAreaForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/switching/ospf-areas')}
      />
    </section>
  );
}

export function OspfAreaEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useOspfArea(id);
  const update = useUpdateOspfArea(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Ospf Area" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Ospf Area." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: OspfAreaInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/switching/ospf-areas/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="ospf-area-edit-title">
      <PageHeader id="ospf-area-edit-title" title={`Edit ${query.data.code}`} />
      <OspfAreaForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/switching/ospf-areas/${id}`)}
      />
    </section>
  );
}
