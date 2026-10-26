import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { LINK_LINK_TYPE_OPTIONS, LINK_CRITICALITY_OPTIONS, LinkLinkType, LinkCriticality } from './types';
import { validateLink, cleanLinkValues } from './schema';
import { LINK_DEFAULTS } from './types';
import type { LinkFormValues, LinkInput } from './types';

interface Props {
  initial?: LinkFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: LinkInput) => void;
  onCancel?: () => void;
}

export function LinkForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<LinkFormValues>({ ...LINK_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof LinkFormValues>(key: K, value: LinkFormValues[K]) {
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
    const result = validateLink(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanLinkValues(values as Record<string, unknown>) as unknown as LinkInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Link form">
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
      <FormField label="A Interface Id" htmlFor="aInterfaceId" error={errors.aInterfaceId} required={true}>
        <Input
          id="aInterfaceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aInterfaceId ?? ''}
          onChange={(v) => set('aInterfaceId', v)}
        />
      </FormField>
      <FormField label="Z Interface Id" htmlFor="zInterfaceId" error={errors.zInterfaceId} required={true}>
        <Input
          id="zInterfaceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zInterfaceId ?? ''}
          onChange={(v) => set('zInterfaceId', v)}
        />
      </FormField>
      <FormField label="Link Type" htmlFor="linkType" error={errors.linkType} required={false}>
        <Select
          id="linkType"
          value={values.linkType ?? ''}
          options={LINK_LINK_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('linkType', (v || undefined) as LinkLinkType | undefined)}
        />
      </FormField>
      <FormField label="Speed Mbps" htmlFor="speedMbps" error={errors.speedMbps} required={false}>
        <NumberInput
          id="speedMbps"
          value={values.speedMbps}
          step={1}
          onChange={(v) => set('speedMbps', v)}
        />
      </FormField>
      <FormField label="Discovered By" htmlFor="discoveredBy" error={errors.discoveredBy} required={false}>
        <Input
          id="discoveredBy"
          value={values.discoveredBy ?? ''}
          maxLength={255}
          onChange={(v) => set('discoveredBy', v)}
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
          options={LINK_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as LinkCriticality | undefined)}
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
