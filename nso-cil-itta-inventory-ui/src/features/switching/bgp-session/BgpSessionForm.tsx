import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { BGP_SESSION_SESSION_STATE_OPTIONS, BGP_SESSION_CRITICALITY_OPTIONS, BgpSessionSessionState, BgpSessionCriticality } from './types';
import { validateBgpSession, cleanBgpSessionValues } from './schema';
import { BGPSESSION_DEFAULTS } from './types';
import type { BgpSessionFormValues, BgpSessionInput } from './types';

interface Props {
  initial?: BgpSessionFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: BgpSessionInput) => void;
  onCancel?: () => void;
}

export function BgpSessionForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<BgpSessionFormValues>({ ...BGPSESSION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof BgpSessionFormValues>(key: K, value: BgpSessionFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateBgpSession(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanBgpSessionValues(values as Record<string, unknown>) as unknown as BgpSessionInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Bgp Session form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Local Device Id" htmlFor="localDeviceId" error={errors.localDeviceId} required={true}>
        <Input
          id="localDeviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.localDeviceId ?? ''}
          onChange={(v) => set('localDeviceId', v)}
        />
      </FormField>
      <FormField label="Peer Address" htmlFor="peerAddress" error={errors.peerAddress} required={true}>
        <Input
          id="peerAddress"
          value={values.peerAddress ?? ''}
          maxLength={255}
          onChange={(v) => set('peerAddress', v)}
        />
      </FormField>
      <FormField label="Local Asn" htmlFor="localAsn" error={errors.localAsn} required={true}>
        <NumberInput
          id="localAsn"
          value={values.localAsn}
          step={1}
          onChange={(v) => set('localAsn', v)}
        />
      </FormField>
      <FormField label="Remote Asn" htmlFor="remoteAsn" error={errors.remoteAsn} required={true}>
        <NumberInput
          id="remoteAsn"
          value={values.remoteAsn}
          step={1}
          onChange={(v) => set('remoteAsn', v)}
        />
      </FormField>
      <FormField label="Session State" htmlFor="sessionState" error={errors.sessionState} required={false}>
        <Select
          id="sessionState"
          value={values.sessionState ?? ''}
          options={BGP_SESSION_SESSION_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('sessionState', (v || undefined) as BgpSessionSessionState | undefined)}
        />
      </FormField>
      <FormField label="Prefix Limit" htmlFor="prefixLimit" error={errors.prefixLimit} required={false}>
        <NumberInput
          id="prefixLimit"
          value={values.prefixLimit}
          step={1}
          onChange={(v) => set('prefixLimit', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={BGP_SESSION_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as BgpSessionCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
