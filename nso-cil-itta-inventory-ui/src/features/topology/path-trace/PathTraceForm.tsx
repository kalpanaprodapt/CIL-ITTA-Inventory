import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { PATH_TRACE_LIFECYCLE_STAGE_OPTIONS, PATH_TRACE_CRITICALITY_OPTIONS, PathTraceLifecycleStage, PathTraceCriticality } from './types';
import { validatePathTrace, cleanPathTraceValues } from './schema';
import { PATHTRACE_DEFAULTS } from './types';
import type { PathTraceFormValues, PathTraceInput } from './types';

interface Props {
  initial?: PathTraceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PathTraceInput) => void;
  onCancel?: () => void;
}

export function PathTraceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PathTraceFormValues>({ ...PATHTRACE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PathTraceFormValues>(key: K, value: PathTraceFormValues[K]) {
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
    const result = validatePathTrace(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPathTraceValues(values as Record<string, unknown>) as unknown as PathTraceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Path Trace form">
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
      <FormField label="Source Ref" htmlFor="sourceRef" error={errors.sourceRef} required={true}>
        <Input
          id="sourceRef"
          value={values.sourceRef ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceRef', v)}
        />
      </FormField>
      <FormField label="Target Ref" htmlFor="targetRef" error={errors.targetRef} required={true}>
        <Input
          id="targetRef"
          value={values.targetRef ?? ''}
          maxLength={255}
          onChange={(v) => set('targetRef', v)}
        />
      </FormField>
      <FormField label="Hop Count" htmlFor="hopCount" error={errors.hopCount} required={false}>
        <NumberInput
          id="hopCount"
          value={values.hopCount}
          step={1}
          onChange={(v) => set('hopCount', v)}
        />
      </FormField>
      <FormField label="Total Latency Ms" htmlFor="totalLatencyMs" error={errors.totalLatencyMs} required={false}>
        <NumberInput
          id="totalLatencyMs"
          value={values.totalLatencyMs}
          step={0.0001}
          onChange={(v) => set('totalLatencyMs', v)}
        />
      </FormField>
      <FormField label="Trace Method" htmlFor="traceMethod" error={errors.traceMethod} required={false}>
        <Input
          id="traceMethod"
          value={values.traceMethod ?? ''}
          maxLength={255}
          onChange={(v) => set('traceMethod', v)}
        />
      </FormField>
      <FormField label="Traced At" htmlFor="tracedAt" error={errors.tracedAt} required={false}>
        <DateTimeInput
          id="tracedAt"
          value={values.tracedAt ?? ''}
          onChange={(v) => set('tracedAt', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={PATH_TRACE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as PathTraceLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={PATH_TRACE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as PathTraceCriticality | undefined)}
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
