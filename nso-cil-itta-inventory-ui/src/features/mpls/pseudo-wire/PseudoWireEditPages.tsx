import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { PseudoWireForm } from './PseudoWireForm';
import { useCreatePseudoWire, useUpdatePseudoWire, usePseudoWire } from './hooks';
import type { PseudoWireInput } from './types';

export function PseudoWireCreatePage() {
  const navigate = useNavigate();
  const create = useCreatePseudoWire();
  const [error, setError] = useState<string>();

  function onSubmit(input: PseudoWireInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/mpls/pseudo-wires/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="pseudo-wire-new-title">
      <PageHeader id="pseudo-wire-new-title" title="New Pseudo Wire" />
      <PseudoWireForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/mpls/pseudo-wires')}
      />
    </section>
  );
}

export function PseudoWireEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = usePseudoWire(id);
  const update = useUpdatePseudoWire(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Pseudo Wire" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Pseudo Wire." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: PseudoWireInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/mpls/pseudo-wires/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="pseudo-wire-edit-title">
      <PageHeader id="pseudo-wire-edit-title" title={`Edit ${query.data.code}`} />
      <PseudoWireForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/mpls/pseudo-wires/${id}`)}
      />
    </section>
  );
}
