import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { ReservationForm } from './ReservationForm';
import { useCreateReservation, useUpdateReservation, useReservation } from './hooks';
import type { ReservationInput } from './types';

export function ReservationCreatePage() {
  const navigate = useNavigate();
  const create = useCreateReservation();
  const [error, setError] = useState<string>();

  function onSubmit(input: ReservationInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/capacity/reservations/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="reservation-new-title">
      <PageHeader id="reservation-new-title" title="New Reservation" />
      <ReservationForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/capacity/reservations')}
      />
    </section>
  );
}

export function ReservationEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useReservation(id);
  const update = useUpdateReservation(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Reservation" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Reservation." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: ReservationInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/capacity/reservations/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="reservation-edit-title">
      <PageHeader id="reservation-edit-title" title={`Edit ${query.data.code}`} />
      <ReservationForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/capacity/reservations/${id}`)}
      />
    </section>
  );
}
